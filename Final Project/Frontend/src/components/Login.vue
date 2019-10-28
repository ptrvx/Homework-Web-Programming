<template>
<div>
    <h1>Login</h1>

    <form id='loginForm' v-on:submit='login($event)'>
        <label>Username:</label>
        <input type='text' id='username' v-model='username' placeholder='username' required>
        <br><br>
        <label>Password:</label>
        <input type='password' id='password' v-model='password' placeholder='password' required  title='Three or more characters'>
        <br><br>
        <button type='submit'>Login</button>
    </form>

</div>
</template>
<script>
    import axios from 'axios'
    import {api} from '../main'

    export default {
        name: 'Login',
        data() {
            return {
                username: '',
                password: ''
            }
        },
        methods: {
            login(event) {
                if(document.getElementById('loginForm').checkValidity()) {
                    document.getElementById('loginForm').checkValidity();
                    axios.post(api + 'users/login', {
                        username: this.username,
                        password: this.password
                    })
                    .then(response => {
                        localStorage.setItem('token', response.data.token)
                        this.$store.commit('setUser', response.data.user)
                        this.$router.push('/')
                    })
                } else {
                    document.getElementById("loginForm").reportValidity();
                }
                event.preventDefault();
            }
        },
        created() {
            const token = localStorage.getItem('token')
            if (!token) {
                this.$router.push('/login')
            } else {
                axios.get(api + 'users', {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('token')
                    }
                })
                .then(response => {
                    this.$store.commit('setUser', response.data)
                    this.$router.push('/')
                });
            }
        }

    }

</script>
<style scoped>
</style>
