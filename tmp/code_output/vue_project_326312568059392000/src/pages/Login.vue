<template>
  <div class="login">
    <div class="container">
      <!-- 面包屑导航 -->
      <div class="breadcrumb">
        <span @click="$router.push('/')">首页</span> > 
        <span>登录</span>
      </div>
      
      <!-- 登录表单 -->
      <div class="login-form">
        <h2>用户登录</h2>
        
        <form @submit.prevent="handleLogin">
          <div class="form-group">
            <label for="username">用户名</label>
            <input 
              type="text" 
              id="username" 
              v-model="username" 
              placeholder="请输入用户名" 
              required 
            />
          </div>
          
          <div class="form-group">
            <label for="password">密码</label>
            <input 
              type="password" 
              id="password" 
              v-model="password" 
              placeholder="请输入密码" 
              required 
            />
          </div>
          
          <div class="form-group remember">
            <label>
              <input type="checkbox" v-model="rememberMe" />
              记住我
            </label>
            <span class="forgot-password" @click="goToForgotPassword">忘记密码？</span>
          </div>
          
          <button type="submit">登录</button>
          
          <div class="links">
            <span @click="$router.push('/register')">没有账号？立即注册</span>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 表单数据
const username = ref('')
const password = ref('')
const rememberMe = ref(false)

// 模拟用户数据库（实际项目中应该是服务器存储）
const users = [
  { username: 'admin', password: '123456', email: 'admin@example.com', phone: '13800138000' }
]

// 处理登录
const handleLogin = () => {
  // 查找匹配的用户
  const user = users.find(u => u.username === username.value && u.password === password.value)
  
  if (user) {
    // 登录成功
    const token = `auth_${Date.now()}_${Math.random().toString(36).substr(2, 9)}`
    
    // 存储认证信息
    if (rememberMe.value) {
      localStorage.setItem('authToken', token)
      localStorage.setItem('rememberMe', 'true')
      localStorage.setItem('username', username.value)
    } else {
      sessionStorage.setItem('authToken', token)
      sessionStorage.setItem('rememberMe', 'false')
      sessionStorage.setItem('username', username.value)
    }
    
    alert(`欢迎回来，${username.value}！`)
    router.push('/')
  } else {
    alert('用户名或密码错误，请重新输入')
  }
}

// 跳转到忘记密码页面
const goToForgotPassword = () => {
  alert('忘记密码功能正在开发中...')
}
</script>

<style>
.login {
  min-height: 100vh;
  background-color: #f8f9fa;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
}

/* 面包屑 */
.breadcrumb {
  color: #666;
  margin-bottom: 2rem;
  cursor: pointer;
}

.breadcrumb span {
  color: #007bff;
}

.breadcrumb span:last-child {
  color: #666;
  cursor: default;
}

/* 登录表单 */
.login-form {
  max-width: 400px;
  margin: 0 auto;
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.login-form h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #333;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.form-group.remember {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.forgot-password {
  color: #007bff;
  cursor: pointer;
  transition: color 0.3s ease;
}

.forgot-password:hover {
  color: #0056b3;
}

.login-form button {
  width: 100%;
  padding: 0.75rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  margin-bottom: 1.5rem;
}

.links {
  text-align: center;
  color: #666;
}

.links span {
  cursor: pointer;
  transition: color 0.3s ease;
}

.links span:hover {
  color: #007bff;
}

@media (max-width: 768px) {
  .login-form {
    margin: 0 1rem;
  }
}</style>