<template>
  <div id="app">
    <header class="app-header">
      <div class="header-container">
        <!-- Logo -->
        <div class="logo">
          <router-link to="/">
            <h1>华智科技</h1>
          </router-link>
        </div>

        <!-- 导航菜单 -->
        <nav class="nav-menu">
          <router-link to="/" active-class="active" exact>首页</router-link>
          <router-link to="/about" active-class="active">关于我们</router-link>
          <router-link to="/products" active-class="active">产品中心</router-link>
          <router-link to="/contact" active-class="active">联系我们</router-link>
        </nav>

        <!-- 移动端菜单按钮 -->
        <div class="mobile-menu-btn" @click="toggleMobileMenu">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>

      <!-- 移动端下拉菜单 -->
      <div class="mobile-menu" :class="{ show: mobileMenuOpen }">
        <router-link to="/" @click="closeMobileMenu" active-class="active" exact>首页</router-link>
        <router-link to="/about" @click="closeMobileMenu" active-class="active">关于我们</router-link>
        <router-link to="/products" @click="closeMobileMenu" active-class="active">产品中心</router-link>
        <router-link to="/contact" @click="closeMobileMenu" active-class="active">联系我们</router-link>
      </div>
    </header>

    <!-- 页面内容 -->
    <main class="app-main">
      <router-view />
    </main>

    <!-- 页脚 -->
    <footer class="app-footer">
      <div class="footer-container">
        <div class="footer-section">
          <h4>华智科技</h4>
          <p>引领智能科技，创造美好未来</p>
        </div>
        
        <div class="footer-section">
          <h4>快速链接</h4>
          <ul>
            <li><router-link to="/">首页</router-link></li>
            <li><router-link to="/about">关于我们</router-link></li>
            <li><router-link to="/products">产品中心</router-link></li>
            <li><router-link to="/contact">联系我们</router-link></li>
          </ul>
        </div>
        
        <div class="footer-section">
          <h4>联系方式</h4>
          <p>电话：400-123-4567</p>
          <p>邮箱：info@huazhi.com</p>
          <p>地址：上海市浦东新区张江高科技园区</p>
        </div>
        
        <div class="footer-section">
          <h4>关注我们</h4>
          <div class="social-links">
            <a href="#" aria-label="微信">WeChat</a>
            <a href="#" aria-label="微博">Weibo</a>
            <a href="#" aria-label="LinkedIn">LinkedIn</a>
            <a href="#" aria-label="GitHub">GitHub</a>
          </div>
        </div>
      </div>
      
      <div class="footer-bottom">
        <p>&copy; 2024 华智科技有限公司. 保留所有权利.</p>
        <p>沪ICP备12345678号 | 沪公网安备 123456789012345678号</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// 移动端菜单状态
const mobileMenuOpen = ref(false)

// 切换移动端菜单
const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value
}

// 关闭移动端菜单
const closeMobileMenu = () => {
  mobileMenuOpen.value = false
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Arial', 'Microsoft YaHei', sans-serif;
  line-height: 1.6;
  color: #333;
  background-color: #f8fafc;
}

#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 头部样式 */
.app-header {
  background: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
}

.logo h1 {
  color: #1a73e8;
  font-size: 1.5rem;
  font-weight: 600;
}

.logo a {
  text-decoration: none;
  color: inherit;
}

.nav-menu {
  display: flex;
  gap: 2rem;
}

.nav-menu a {
  text-decoration: none;
  color: #333;
  font-weight: 500;
  transition: color 0.3s ease;
  position: relative;
}

.nav-menu a:hover {
  color: #1a73e8;
}

.nav-menu a.active {
  color: #1a73e8;
}

.nav-menu a.active::after {
  content: '';
  position: absolute;
  bottom: -5px;
  left: 0;
  width: 100%;
  height: 2px;
  background: #1a73e8;
}

/* 移动端菜单按钮 */
.mobile-menu-btn {
  display: none;
  flex-direction: column;
  cursor: pointer;
}

.mobile-menu-btn span {
  width: 25px;
  height: 3px;
  background: #333;
  margin: 3px 0;
  transition: 0.3s;
  border-radius: 2px;
}

/* 移动端下拉菜单 */
.mobile-menu {
  position: absolute;
  top: 70px;
  left: 0;
  right: 0;
  background: white;
  box-shadow: 0 5px 10px rgba(0,0,0,0.1);
  display: none;
  flex-direction: column;
  padding: 1rem;
  gap: 1rem;
}

.mobile-menu a {
  padding: 0.8rem 1rem;
  text-decoration: none;
  color: #333;
  border-radius: 6px;
  transition: background 0.3s ease;
}

.mobile-menu a:hover {
  background: #f0f4f8;
}

.mobile-menu a.active {
  background: #e3f2fd;
  color: #1a73e8;
}

/* 主要内容区域 */
.app-main {
  flex: 1;
}

/* 页脚样式 */
.app-footer {
  background: #1a73e8;
  color: white;
  padding-top: 3rem;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 2rem;
  margin-bottom: 2rem;
}

.footer-section h4 {
  margin-bottom: 1rem;
  font-size: 1.1rem;
}

.footer-section p,
.footer-section li {
  color: rgba(255,255,255,0.8);
  margin-bottom: 0.5rem;
}

.footer-section ul {
  list-style: none;
}

.footer-section ul li {
  margin-bottom: 0.5rem;
}

.footer-section ul li a {
  color: rgba(255,255,255,0.8);
  text-decoration: none;
  transition: color 0.3s ease;
}

.footer-section ul li a:hover {
  color: white;
}

.social-links {
  display: flex;
  gap: 1rem;
}

.social-links a {
  color: rgba(255,255,255,0.8);
  text-decoration: none;
  transition: color 0.3s ease;
}

.social-links a:hover {
  color: white;
}

.footer-bottom {
  text-align: center;
  padding: 1.5rem 0;
  border-top: 1px solid rgba(255,255,255,0.1);
  color: rgba(255,255,255,0.6);
  font-size: 0.9rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .nav-menu {
    display: none;
  }

  .mobile-menu-btn {
    display: flex;
  }

  .mobile-menu.show {
    display: flex;
  }

  .header-container {
    height: 60px;
  }

  .logo h1 {
    font-size: 1.3rem;
  }

  .footer-container {
    grid-template-columns: 1fr;
    text-align: center;
    gap: 1.5rem;
  }

  .footer-section ul {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 1rem;
  }

  .social-links {
    justify-content: center;
  }
}
</style>