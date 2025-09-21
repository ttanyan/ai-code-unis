import { createRouter, createWebHashHistory } from 'vue-router'

// 导入页面组件
import Home from '../pages/Home.vue'
import Products from '../pages/Products.vue'
import ProductDetail from '../pages/ProductDetail.vue'
import Cart from '../pages/Cart.vue'
import Orders from '../pages/Orders.vue'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'

// 定义路由
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/products',
    name: 'Products',
    component: Products
  },
  {
    path: '/product/:id',
    name: 'ProductDetail',
    component: ProductDetail
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart
  },
  {
    path: '/orders',
    name: 'Orders',
    component: Orders
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  }
]

// 创建路由器实例
const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router