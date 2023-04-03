import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'
import index from "@/store";
Vue.use(VueRouter)

const routes = [

  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//动态路由
export const setRoutes= () =>{
  const storeMenus = localStorage.getItem("menus");
  //拼装
  if(storeMenus){
    const manageRoute = {path: '/', name: 'Manage', component: () => import('../views/Manage.vue'),redirect:"/home", children:[]}
    const menus = JSON.parse(storeMenus)
    menus.forEach(item =>{
      let itemMenu = {path: item.path.replace("/",""), name: itme.name, component:() => import(`../view/${item.pagePath}`)}
      manageRoute.children.push(itemMenu)
    })
    //添加到路由对象中
    router.addRoute(manageRoute)
  }

}
router.addRoute({
  path: '/',
  name: 'Manage',
  component: () => import('../views/Manage.vue'),
  //默认访问主页
  redirect:"/home",
  children:[
    {path: 'user',name:'用户管理',component: () => import('../views/User.vue')},
    {path: 'role',name:'角色管理',component: () => import('../views/Role.vue')},
    {path: 'menu',name:'菜单管理',component: () => import('../views/Menu.vue')},
    {path: 'Home',name:'主页',component: () => import('../views/Home.vue')},
    {path: '/person',name: '个人信息',component: () => import('../views/Person.vue')},
    {path: '/file',name: '文件管理',component: () => import('../views/File.vue')}
  ],
},)


//路由守卫
router.beforeEach((to,from, next)=>{//路由名称
  localStorage.setItem("currentPathName", to.name)  //设置路由名称
  index.commit("setPath")  ///触发store的数据更新
  next()//放行路由
})
export default router