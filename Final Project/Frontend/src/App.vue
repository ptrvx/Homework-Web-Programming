<template>
<div id="app">
	<div>

		<nav class="nav">
			<router-link :to="'/'" class="nav-link">Home</router-link>
			<router-link :to="'/create'" class="nav-link" v-if="authorized">Create User</router-link>
			<button class='nav-link btn btn-primary' type="button submit" v-if='authenticated' v-on:click='logout'>Logout</button>
		</nav>
		
	</div>

	<div>
		{{ fullName }}
	</div>
	<div>
		<router-view/>
	</div>
</div>
</template>

<script>
import axios from 'axios';
import {api} from './main';

export default {
	name: 'app',

	data() {
		return {
			coupons: [],
			shops: []
		};
	},

	methods: {
		logout() {
			this.$store.commit('setUser', null);
			localStorage.clear();
			this.$router.push('/login');
		}
	},
	computed: {
		fullName () {
			if(!this.$store.state.user) {
				return '';
			}
			let firstName = this.$store.state.user.firstName;
			if (!firstName) {
				firstName = '';
			}
			let lastName = this.$store.state.user.lastName;
			if (!lastName) {
				lastName = '';
			}
			return firstName + ' ' + lastName;
		},
		authorized() {
			if(!this.$store.state.user) {
				return false;
			}
			return this.$store.state.user.privilege === 'ADMINISTRATOR';
		},
		authenticated() {
			return this.$store.state.user;

		}
	},
	created() {
		const token = localStorage.getItem('token');
		if (!token) {
			this.$router.push('/login');
		}
		else {
			axios
				.get(api + 'users', {
					headers: {
						Authorization: 'Bearer ' + localStorage.getItem('token')
					}
				})
				.then(response => {
					this.$store.commit('setUser', response.data);
				})
				.catch(() => {
					this.logout()
				});
		}
	},
};
</script>

<style>
#app {
	font-family: "Avenir", Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
	margin-top: 60px;
	
}

table {
  border-collapse: collapse;
}

table, th, td {
  border: 1px solid black;
  padding: 6px;
}
</style>
