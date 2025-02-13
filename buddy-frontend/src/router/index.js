import Profile from '../views/Profile.vue'

// Add this to your routes array
{
  path: '/profile',
  name: 'Profile',
  component: Profile,
  meta: { requiresAuth: true }
}