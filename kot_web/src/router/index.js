import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/pk/PkIndexView.vue'
import NotFoundView from '../views/error/NotFoundView.vue'
import SpaceView from '../views/space/SpaceView.vue'
import UserView from '../views/user/UserView.vue'
import UserListView from '../views/userlist/UserListView.vue'
import UserAccountLogin from '../views/user/account/UserAccountLogin.vue'
import UserAccountRegister from '../views/user/account/UserAccountRegister.vue'

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/pk/"
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PkIndexView,
  },
  {
    path: "/space/",
    name: "space_index",
    component: SpaceView,
  },
  {
    path: "/user/",
    name: "user_index",
    component: UserView,
  },
  {
    path: "/userlist/",
    name: "userlist_index",
    component: UserListView,
  },
  {
    path: "/user/account/login/",
    name: "user_account_login",
    component: UserAccountLogin,
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component: UserAccountRegister,
  },
  {
    path: "/404/",
    name: "404",
    component: NotFoundView,
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/"
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
