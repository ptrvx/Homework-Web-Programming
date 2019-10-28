import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'
import Shop from './components/Shop'
import Coupons from './components/Coupons'
import Login from './components/Login'
import Create from './components/Create'

Vue.use(Vuex)
Vue.config.productionTip = false

export const api = 'http://localhost:8080/app/rest/';

Vue.use(Vuex);
const store = new Vuex.Store({
    state: {
        user: null,
    },
    mutations: {
        setUser (state, newUser) {
			state.user = newUser
        }
    }
});

const routes = [
	{
		path: '/',
		component: Coupons,
		name: 'home'
	},
	{
		path: '/shop/:id',
		component: Shop,
		name: 'shop'
	},
	{
		path: '/login',
		component: Login,
		name: 'login'
	},
	{
		path: '/create',
		component: Create,
		name: 'create'
	},
	{
		path: '*', 
		redirect: '/' 
	}
];

Vue.use(VueRouter);
const router = new VueRouter({
	mode: 'history',
	routes
});

new Vue({
	router,
	store,
	render: h => h(App),
}).$mount('#app')
