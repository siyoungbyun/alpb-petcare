import { createRouter, createWebHistory } from 'vue-router'
import Home from './views/Home.vue'
import PaymentTest from './components/PaymentTest.vue'
import { useAuth } from './store/auth'
import { api } from './services/api'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/pet-management',
    name: 'PetManagement',
    component: () => import('./views/PetManagement.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/service-booking',
    name: 'ServiceBooking',
    component: () => import('./views/ServiceBooking.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: () => import('./views/SignUp.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('./views/Login.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('./views/Profile.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile/edit',
    name: 'UpdateProfile',
    component: () => import('./views/UpdateProfile.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/withdrawal',
    name: 'Withdrawal',
    component: () => import('./views/Withdrawal.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/pet-service-registration',
    name: 'PetServiceRegistration',
    component: () => import('./views/PetServiceRegistration.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/pet-services',
    name: 'PetServiceList',
    component: () => import('./views/PetServiceList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/payment',
    name: 'PaymentTest',
    component: PaymentTest,
    meta: { requiresAuth: false }
  },
  {
    path: '/code-management',
    name: 'CodeManagement',
    component: () => import('./views/CodeManagement.vue'),
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Add navigation guard for protected routes
router.beforeEach(async (to, from, next) => {
  if (to.meta.requiresAuth) {
    try {
      const isAuthenticated = await api.checkAuth()
      useAuth.setLoggedIn(isAuthenticated)

      if (!isAuthenticated) {
        next('/login')
        return
      }
    } catch (error) {
      console.error('Auth check failed:', error)
      next('/login')
      return
    }
  }
  next()
})

export default router