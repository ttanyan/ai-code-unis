<template>
  <div id="app">
    <!-- 顶部导航栏 -->
    <header class="header">
      <div class="container">
        <!-- 网站Logo和名称 -->
        <div class="logo" @click="$router.push('/')">
          <span>电商商城</span>
        </div>
        
        <!-- 搜索框 -->
        <div class="search-box">
          <input 
            type="text" 
            v-model="searchKeyword" 
            placeholder="搜索商品..." 
            @keyup.enter="searchProducts"
          />
          <button @click="searchProducts">搜索</button>
        </div>
        
        <!-- 用户操作 -->
        <div class="user-actions">
          <!-- 登录/注册状态 -->
          <template v-if="isLoggedIn">
            <span class="welcome">欢迎，{{ username }}</span>
            <button @click="goToOrders">我的订单</button>
            <button @click="logout">退出登录</button>
          </template>
          <template v-else>
            <button @click="goToLogin">登录</button>
            <button @click="goToRegister">注册</button>
          </template>
          
          <!-- 购物车 -->
          <div class="cart-icon" @click="goToCart">
            <span>🛒</span>
            <span class="cart-count" v-if="cartItemCount > 0">{{ cartItemCount }}</span>
          </div>
        </div>
      </div>
    </header>
    
    <!-- 主要内容区域 -->
    <main class="main">
      <router-view />
    </main>
    
    <!-- 底部 -->
    <footer class="footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-section">
            <h3>关于我们</h3>
            <p>电商商城是一家专注于为消费者提供高品质商品的在线购物平台。我们致力于为您提供优质的服务和愉快的购物体验。</p>
          </div>
          
          <div class="footer-section">
            <h3>快速链接</h3>
            <ul>
              <li @click="$router.push('/')">首页</li>
              <li @click="$router.push('/products')">商品列表</li>
              <li @click="$router.push('/cart')">购物车</li>
              <li @click="$router.push('/orders')">我的订单</li>
            </ul>
          </div>
          
          <div class="footer-section">
            <h3>客户服务</h3>
            <ul>
              <li>客服电话：400-123-4567</li>
              <li>服务时间：周一至周日 9:00-21:00</li>
              <li>邮箱：service@ecommerce.com</li>
            </ul>
          </div>
          
          <div class="footer-section">
            <h3>关注我们</h3>
            <div class="social-links">
              <span>微信</span>
              <span>微博</span>
              <span>抖音</span>
            </div>
          </div>
        </div>
        
        <div class="footer-bottom">
          <p>&copy; 2024 电商商城. 保留所有权利.</p>
          <p>地址：中国某市某区某路123号 | 备案号：京ICP备12345678号</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 搜索关键词
const searchKeyword = ref('')

// 用户登录状态
const isLoggedIn = ref(false)
const username = ref('')

// 购物车商品数量
const cartItemCount = computed(() => {
  const cart = JSON.parse(localStorage.getItem('cart') || '[]')
  return cart.reduce((count, item) => count + item.quantity, 0)
})

// 组件挂载时检查登录状态
onMounted(() => {
  checkAuthStatus()
})

// 检查认证状态
const checkAuthStatus = () => {
  const token = localStorage.getItem('authToken') || sessionStorage.getItem('authToken')
  const savedUsername = localStorage.getItem('username') || sessionStorage.getItem('username')
  
  if (token && savedUsername) {
    isLoggedIn.value = true
    username.value = savedUsername
  }
}

// 搜索商品
const searchProducts = () => {
  if (searchKeyword.value.trim()) {
    router.push({
      path: '/products',
      query: { keyword: searchKeyword.value.trim() }
    })
  }
}

// 导航到登录
const goToLogin = () => {
  router.push('/login')
}

// 导航到注册
const goToRegister = () => {
  router.push('/register')
}

// 导航到订单
const goToOrders = () => {
  router.push('/orders')
}

// 导航到购物车
const goToCart = () => {
  router.push('/cart')
}

// 退出登录
const logout = () => {
  if (confirm('确定要退出登录吗？')) {
    // 清除认证信息
    localStorage.removeItem('authToken')
    localStorage.removeItem('rememberMe')
    localStorage.removeItem('username')
    sessionStorage.removeItem('authToken')
    sessionStorage.removeItem('rememberMe')
    sessionStorage.removeItem('username')
    
    isLoggedIn.value = false
    username.value = ''
    
    alert('已成功退出登录')
    
    // 如果在订单页面，跳转到首页
    if (router.currentRoute.value.path === '/orders') {
      router.push('/')
    }
  }
}
</script>

<style>
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 顶部导航栏 */
.header {
  background-color: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.header .container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 80px;
}

/* Logo */
.logo {
  font-size: 1.5rem;
  font-weight: bold;
  color: #e74c3c;
  cursor: pointer;
}

/* 搜索框 */
.search-box {
  flex: 1;
  max-width: 500px;
  margin: 0 2rem;
}

.search-box input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px 0 0 4px;
  font-size: 1rem;
}

.search-box button {
  padding: 0.75rem 1.5rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 0 4px 4px 0;
  cursor: pointer;
}

/* 用户操作 */
.user-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-actions button {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  background-color: white;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.user-actions button:hover {
  background-color: #f8f9fa;
}

.welcome {
  color: #333;
}

.cart-icon {
  position: relative;
  font-size: 1.5rem;
  cursor: pointer;
}

.cart-count {
  position: absolute;
  top: -8px;
  right: -8px;
  background-color: #e74c3c;
  color: white;
  border-radius: 50%;
  width: 18px;
  height: 18px;
  font-size: 0.75rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 主要内容区域 */
.main {
  flex: 1;
}

/* 底部 */
.footer {
  background-color: #333;
  color: white;
  padding: 3rem 0 1.5rem 0;
  margin-top: auto;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 2rem;
  margin-bottom: 2rem;
}

.footer-section h3 {
  margin-bottom: 1rem;
  color: #fff;
}

.footer-section p,
.footer-section li {
  color: #ccc;
  margin: 0.5rem 0;
}

.footer-section ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-section ul li {
  cursor: pointer;
  transition: color 0.3s ease;
}

.footer-section ul li:hover {
  color: #fff;
}

.social-links {
  display: flex;
  gap: 1rem;
}

.social-links span {
  cursor: pointer;
  transition: color 0.3s ease;
}

.social-links span:hover {
  color: #fff;
}

.footer-bottom {
  text-align: center;
  padding-top: 1.5rem;
  border-top: 1px solid #444;
  color: #ccc;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .header .container {
    flex-direction: column;
    height: auto;
    padding: 1rem 0;
  }
  
  .search-box {
    margin: 1rem 0;
    max-width: 100%;
  }
  
  .user-actions {
    width: 100%;
    justify-content: center;
    margin-top: 1rem;
  }
  
  .cart-icon {
    position: absolute;
    top: 1rem;
    right: 1rem;
  }
}</style>