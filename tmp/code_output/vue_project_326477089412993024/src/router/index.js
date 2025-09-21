import { createRouter, createWebHashHistory } from 'vue-router'

// 引入页面组件
const Home = () => import('../pages/Home.vue')
const About = () => import('../pages/About.vue')
const Products = () => import('../pages/Products.vue')
const Contact = () => import('../pages/Contact.vue')

// 定义路由
const routes = [
  { path: '/', component: Home },
  { path: '/about', component: About },
  { path: '/products', component: Products },
  { path: '/contact', component: Contact }
]

// 创建路由器实例
const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router