<template>
    <div class="container">
        <!-- 左侧聊天区域 -->
        <div class="chat-section">
            <div class="chat-header">
                咖啡连锁店客服中心
            </div>
            <div class="chat-messages">
                <div v-for="(message, index) in messages" :key="index" :class="['message', message.type]">
                    {{ message.text }}
                </div>
            </div>
            <div class="chat-input">
                <input v-model="userInput" @keyup.enter="sendMessage" placeholder="请输入您的问题...">
                <button @click="sendMessage">发送</button>
            </div>
        </div>
        
        <!-- 右侧表格区域 -->
        <div class="info-section">
            <div class="tab-header">
                <div class="tab" :class="{ active: activeTab === 'order-list' }" @click="switchTab('order-list')">订单列表</div>
                <div class="tab" :class="{ active: activeTab === 'coffee-menu' }" @click="switchTab('coffee-menu')">咖啡菜单</div>
            </div>
            
            <!-- 订单列表表格 -->
            <div v-show="activeTab === 'order-list'" class="tab-content">
                <h3>最近订单</h3>
                <table>
                    <thead>
                        <tr>
                            <th>订单号</th>
                            <th>客人</th>
                            <th>折扣</th>
                            <th>金额</th>
                            <th>状态</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="order in orderList" :key="order.orderNumber">
                            <td>{{ order.id }}</td>
                            <td>{{ order.customer }}</td>
                            <td>{{ order.discount }}</td>
                            <td>{{ order.total }}</td>
                            <td><span :class="['order-status', order.state]">{{ order.state }}</span></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
            <!-- 咖啡菜单表格 -->
            <div v-show="activeTab === 'coffee-menu'" class="tab-content">
                <h3>在售咖啡列表</h3>
                <table>
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>描述</th>
                            <th>价格</th>
                            <th>库存</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="coffee in coffeeList" :key="coffee.name">
                            <td>{{ coffee.name }}</td>
                            <td>{{ coffee.description }}</td>
                            <td>{{ coffee.price }}</td>
                            <td>{{ coffee.stock }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'; // 引入axios
import { v4 as uuidv4 } from 'uuid'; // 引入uuid生成器，需要先安装：npm install uuid

export default {
    data() {
        return {
            userInput: '',
            messages: [
                { text: '欢迎光临咖啡连锁店客服系统，有什么可以帮您?', type: 'service-message' },
                { text: '我们提供多种咖啡，您可以查看右侧菜单。', type: 'service-message' }
            ],
            activeTab: 'order-list', // 默认显示订单列表
            coffeeList: [], // 初始化为空数组
            orderList: [], // 初始化为空数组
            pollingInterval: null, // 用于存储定时器ID
            chatId: uuidv4() // 生成唯一的chatId
        };
    },
    mounted() {
        this.fetchCoffeeList(); // 组件挂载后获取咖啡列表
        this.fetchOrderList(); // 组件挂载后获取订单列表
        this.startPolling(); // 开始轮询
        this.scrollToBottom(); // 初始滚动到底部
    },
    updated() {
        // 在组件更新后滚动到底部
        this.scrollToBottom();
    },
    beforeUnmount() {
        clearInterval(this.pollingInterval); // 清理定时器
    },
    methods: {
        fetchCoffeeList() {
            axios.get('/customer-service/customer/menu')
                .then(response => {
                    this.coffeeList = response.data; // 假设返回的数据格式正确
                })
                .catch(error => {
                    console.error('获取咖啡列表失败:', error);
                });
        },
        fetchOrderList() {
            axios.get('/waiter-service/order/') // 更新为新的API地址
                .then(response => {
                    this.orderList = response.data; // 假设返回的数据格式正确
                })
                .catch(error => {
                    console.error('获取订单列表失败:', error);
                });
        },
        startPolling() {
            this.pollingInterval = setInterval(() => {
                this.fetchOrderList(); // 每隔一定时间获取最新订单
            }, 5000); // 每5秒请求一次
        },
        scrollToBottom() {
            // 使用nextTick确保DOM已更新
            this.$nextTick(() => {
                const chatMessages = this.$el.querySelector('.chat-messages');
                if (chatMessages) {
                    chatMessages.scrollTop = chatMessages.scrollHeight;
                }
            });
        },
        sendMessage() {
            if (this.userInput.trim()) {
                // 添加用户消息到聊天窗口
                this.messages.push({ text: this.userInput, type: 'user-message' });
                
                // 准备发送到后端的数据
                const messageData = {
                    userMessage: this.userInput,
                    chatId: this.chatId
                };
                
                // 清空输入框
                this.userInput = '';
                
                // 发送消息到后端API
                axios.post('/chat-service/api/assistant/chat', messageData)
                    .then(response => {
                        // 添加后端返回的回复到聊天窗口
                        this.messages.push({ text: response.data || '感谢您的咨询，我们会尽快回复您。', type: 'service-message' });
                        // 滚动到底部
                        this.scrollToBottom();
                    })
                    .catch(error => {
                        console.error('发送消息失败:', error);
                        // 发生错误时显示错误消息
                        this.messages.push({ text: '抱歉，服务暂时不可用，请稍后再试。', type: 'service-message' });
                        // 滚动到底部
                        this.scrollToBottom();
                    });
            }
        },
        switchTab(tab) {
            this.activeTab = tab;
        }
    }
}
</script>

<style scoped>
/* 在此处添加CSS样式，参考之前的样式 */
.container {
    display: flex;
    width: 100%;
    max-width: 1400px;
    margin: 20px auto;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    height: 80vh; /* 设置容器高度 */
}

/* 左侧聊天区域 */
.chat-section {
    flex: 1;
    display: flex;
    flex-direction: column;
    border-right: 1px solid #e0e0e0;
    background-color: #fff;
    height: 100%; /* 确保聊天区域占满容器高度 */
}

.chat-header {
    padding: 15px;
    background-color: #5c4033;
    color: white;
    font-size: 18px;
    text-align: center;
}

.chat-messages {
    flex: 1;
    padding: 15px;
    overflow-y: auto; /* 添加垂直滚动条 */
    background-color: #f9f9f9;
    height: 500px; /* 设置固定高度 */
    max-height: calc(100% - 120px); /* 减去头部和输入框的高度 */
    display: flex;
    flex-direction: column;
}

.message {
    margin-bottom: 15px;
    padding: 10px;
    border-radius: 10px;
    max-width: 80%;
    word-wrap: break-word; /* 确保长文本能够换行 */
}

.user-message {
    background-color: #e1f5fe;
    align-self: flex-end;
    margin-left: auto;
}

.service-message {
    background-color: #fff;
    border: 1px solid #e0e0e0;
    align-self: flex-start;
}

.chat-input {
    display: flex;
    padding: 10px;
    background-color: #fff;
    border-top: 1px solid #e0e0e0;
}

.chat-input input {
    flex: 1;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    outline: none;
}

.chat-input button {
    margin-left: 10px;
    padding: 10px 15px;
    background-color: #5c4033;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

/* 右侧表格区域 */
.info-section {
    flex: 1;
    display: flex;
    flex-direction: column;
    background-color: #fff;
    padding: 0;
}

.tab-header {
    display: flex;
    background-color: #f0f0f0;
}

.tab {
    padding: 15px;
    cursor: pointer;
    flex: 1;
    text-align: center;
    font-weight: bold;
}

.tab.active {
    background-color: #5c4033;
    color: white;
}

.tab-content {
    flex: 1;
    padding: 15px;
    overflow-y: auto;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}

th, td {
    padding: 12px 15px;
    text-align: left;
    border-bottom: 1px solid #ddd;
}

th {
    background-color: #f5f5f5;
    font-weight: bold;
}

tr:hover {
    background-color: #f9f9f9;
}

.order-status {
    padding: 5px 8px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: bold;
}

.status-pending {
    background-color: #fff3cd;
    color: #856404;
}

.status-completed {
    background-color: #d4edda;
    color: #155724;
}
</style> 