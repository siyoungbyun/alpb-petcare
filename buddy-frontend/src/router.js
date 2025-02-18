import { createRouter, createWebHistory } from 'vue-router'
import Home from './views/Home.vue'
import PaymentTest from './components/PaymentTest.vue'
import { useAuth } from './store/auth'
import { api } from './services/api'
import PetsitterProfile from './views/PetsitterProfile.vue'
import PetServiceList from './views/PetServiceList.vue'

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
    component: PetServiceList,
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
  },
  {
    path: '/petsitter-profile',
    name: 'PetsitterProfile',
    component: PetsitterProfile,
    meta: { requiresAuth: true }
  },
  {
    path: '/booking-management',
    name: 'BookingManagement',
    component: () => import('./views/BookingManagement.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/my-bookings',
    name: 'UserBookings',
    component: () => import('./views/UserBookings.vue'),
    meta: { requiresAuth: true }
  },
  // Catch-all route
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('./views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Add navigation guard for protected routes
router.beforeEach(async (to, from, next) => {
  if (to.meta.requiresAuth) {
    console.log(`Route ${to.path} requires authentication.`)
    try {
      const isAuthenticated = await api.checkAuth()
      console.log('Authentication status:', isAuthenticated)
      useAuth.setLoggedIn(isAuthenticated)

      if (!isAuthenticated) {
        console.log('User not authenticated. Redirecting to /login.')
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