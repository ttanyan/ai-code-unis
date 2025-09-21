<template>
  <div class="register">
    <div class="container">
      <!-- 面包屑导航 -->
      <div class="breadcrumb">
        <span @click="$router.push('/')">首页</span> > 
        <span>注册</span>
      </div>
      
      <!-- 注册表单 -->
      <div class="register-form">
        <h2>用户注册</h2>
        
        <form @submit.prevent="handleRegister">
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
            <label for="email">邮箱</label>
            <input 
              type="email" 
              id="email" 
              v-model="email" 
              placeholder="请输入邮箱" 
              required 
            />
          </div>
          
          <div class="form-group">
            <label for="phone">手机号</label>
            <input 
              type="tel" 
              id="phone" 
              v-model="phone" 
              placeholder="请输入手机号" 
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
          
          <div class="form-group">
            <label for="confirmPassword">确认密码</label>
            <input 
              type="password" 
              id="confirmPassword" 
              v-model="confirmPassword" 
              placeholder="请再次输入密码" 
              required 
            />
          </div>
          
          <div class="form-group agreement">
            <label>
              <input type="checkbox" v-model="agreement" required />
              我已阅读并同意 <span class="link" @click="showAgreement">《用户协议》</span> 和 <span class="link" @click="showPrivacy">《隐私政策》</span>
            </label>
          </div>
          
          <button type="submit">注册</button>
          
          <div class="links">
            <span @click="$router.push('/login')">已有账号？立即登录</span>
          </div>
        </form>
      </div>
      
      <!-- 用户协议弹窗 -->
      <div class="modal" v-if="showAgreementModal">
        <div class="modal-content">
          <div class="modal-header">
            <h3>用户协议</h3>
            <span class="close" @click="closeAgreement">×</span>
          </div>
          <div class="modal-body">
            <p><strong>欢迎使用我们的服务！</strong></p>
            <p>本协议是您与电商商城之间关于您使用我们提供的各项服务所订立的协议。请您仔细阅读本协议，特别是加粗显示的条款。</p>
            
            <h4>1. 服务内容</h4>
            <p>我们为您提供商品浏览、购买、订单管理等电子商务服务。</p>
            
            <h4>2. 用户义务</h4>
            <p>您应当妥善保管您的账户信息，对账户下的所有行为负责。</p>
            
            <h4>3. 知识产权</h4>
            <p>本网站所有内容的知识产权归电商商城所有。</p>
            
            <h4>4. 免责声明</h4>
            <p>我们不对因网络故障、系统维护等原因导致的服务中断承担责任。</p>
            
            <h4>5. 协议修改</h4>
            <p>我们有权随时修改本协议，修改后的协议将在网站公布后生效。</p>
          </div>
          <div class="modal-footer">
            <button @click="closeAgreement">确定</button>
          </div>
        </div>
      </div>
      
      <!-- 隐私政策弹窗 -->
      <div class="modal" v-if="showPrivacyModal">
        <div class="modal-content">
          <div class="modal-header">
            <h3>隐私政策</h3>
            <span class="close" @click="closePrivacy">×</span>
          </div>
          <div class="modal-body">
            <p><strong>我们重视您的隐私保护！</strong></p>
            <p>本政策说明我们如何收集、使用、保护您的个人信息。</p>
            
            <h4>1. 信息收集</h4>
            <p>我们仅收集您在注册和购物过程中提供的必要信息，包括用户名、邮箱、手机号等。</p>
            
            <h4>2. 信息使用</h4>
            <p>收集的信息仅用于为您提供服务、处理订单、发送通知等目的。</p>
            
            <h4>3. 信息保护</h4>
            <p>我们采取合理的技术和管理措施保护您的信息安全。</p>
            
            <h4>4. 信息共享</h4>
            <p>我们不会向第三方出售您的个人信息，除非获得您的明确同意或法律要求。</p>
            
            <h4>5. 您的权利</h4>
            <p>您有权查询、更正、删除您的个人信息，或注销您的账户。</p>
          </div>
          <div class="modal-footer">
            <button @click="closePrivacy">确定</button>
          </div>
        </div>
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
const email = ref('')
const phone = ref('')
const password = ref('')
const confirmPassword = ref('')
const agreement = ref(false)

// 弹窗控制
const showAgreementModal = ref(false)
const showPrivacyModal = ref(false)

// 显示用户协议
const showAgreement = () => {
  showAgreementModal.value = true
}

// 显示隐私政策
const showPrivacy = () => {
  showPrivacyModal.value = true
}

// 关闭用户协议
const closeAgreement = () => {
  showAgreementModal.value = false
}

// 关闭隐私政策
const closePrivacy = () => {
  showPrivacyModal.value = false
}

// 处理注册
const handleRegister = () => {
  // 基本验证
  if (password.value !== confirmPassword.value) {
    alert('两次输入的密码不一致')
    return
  }
  
  if (password.value.length < 6) {
    alert('密码长度不能少于6位')
    return
  }
  
  // 模拟检查用户名是否已存在（实际项目中应该是服务器验证）
  const users = JSON.parse(localStorage.getItem('users') || '[]')
  const existingUser = users.find(u => u.username === username.value)
  
  if (existingUser) {
    alert('该用户名已被注册，请选择其他用户名')
    return
  }
  
  // 保存用户信息
  const newUser = {
    username: username.value,
    email: email.value,
    phone: phone.value,
    password: password.value
  }
  
  users.push(newUser)
  localStorage.setItem('users', JSON.stringify(users))
  
  alert(`注册成功！欢迎您，${username.value}！\n\n现在可以使用您的账号登录了。`)
  router.push('/login')
}
</script>

<style>
.register {
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

/* 注册表单 */
.register-form {
  max-width: 500px;
  margin: 0 auto;
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.register-form h2 {
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

.form-group.agreement {
  margin-bottom: 1.5rem;
}

.link {
  color: #007bff;
  cursor: pointer;
  transition: color 0.3s ease;
}

.link:hover {
  color: #0056b3;
}

.register-form button {
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

/* 弹窗样式 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  border-radius: 8px;
  width: 90%;
  max-width: 600px;
  max-height: 80vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid #eee;
}

.modal-header h3 {
  margin: 0;
  color: #333;
}

.close {
  font-size: 1.5rem;
  color: #666;
  cursor: pointer;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close:hover {
  color: #000;
  background-color: #f8f9fa;
  border-radius: 50%;
}

.modal-body {
  padding: 1rem;
}

.modal-body p {
  margin: 0.5rem 0;
  color: #666;
  line-height: 1.6;
}

.modal-body h4 {
  margin: 1rem 0 0.5rem 0;
  color: #333;
}

.modal-footer {
  padding: 1rem;
  border-top: 1px solid #eee;
  text-align: right;
}

.modal-footer button {
  padding: 0.5rem 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

@media (max-width: 768px) {
  .register-form {
    margin: 0 1rem;
    padding: 1.5rem;
  }
  
  .modal-content {
    width: 95%;
  }
}</style>