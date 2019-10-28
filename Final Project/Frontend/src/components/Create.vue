<template>
<div class='container'>
    <form>

        <input type='text' id='firstName' v-model='firstName' placeHolder='First Name'>
        <br>
        
        <input type='text' id='lastName' v-model='lastName' placeholder="lastName">
        <br>

        <input type='text' id="username" v-model="username" placeholder="username">
        <br>

        <input type="password" id='password' v-model="password" placeholder="password">
        <br>

        <label>Privilege Level: </label>
        <select id='privilege' v-model='privilege'>
            <option>ADMINISTRATOR</option>
            <option>OPERATOR</option>
        </select>
        <button type="button" class='btn btn-primary' v-on:click='create'>Create</button>
    </form>
</div>
</template>
<script>

    import {api} from '../main';
    import axios from 'axios';

    export default {
        name: 'Create',

        data() {
            return {
                firstName: '',
                lastName: '',
                username: '',
                password: '',
                privilege: ''
            }
        },
        methods: {
            create() {
                axios.post(api + 'users', {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    username: this.username,
                    password: this.password,
                    privilege: this.privilege
                }, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('token')
                    }
                })
                .then(() => {
                    this.$router.push('/')
                })
            }
        },
        created() {

        }

    }


</script>
<style scoped>

</style>