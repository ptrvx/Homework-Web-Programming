<template>
<div class='container'>
    <h1>{{ shop.name }}</h1>

    <button v-on:click='deleteShop'>Delete Shop</button>

    <table v-if='shop.coupons.length > 0'>
        <thead>
            <tr>
                <th scope='col'>Product Name</th>
                <th scope='col'>Discounted Price</th>
                <th scope='col'>Original Price</th>
                <th scope='col'>Sale</th>
            </tr>
        </thead>
        <tbody>
            <tr v-bind:key='coupon.id' v-for='coupon in this.shop.coupons'>
                <td>{{ coupon.product }}</td>
                <td>{{ coupon.discountedPrice }}</td>
                <td>{{ coupon.originalPrice }}</td>
                <td>{{ coupon.sale }}</td>
            </tr>
        </tbody>
    </table>
    <div v-if='shop.coupons.length===0'>No coupons in the shop</div>
</div>
</template>
<script>
    import {api} from '../main';
    import axios from 'axios';

    export default {
        name: 'Shop',

        data() {
            return {
                shop: {
                    name: '',
                    id: '',
                    coupons: []
                },
            };
        },
        computed: {

        },
        methods: {
            load() {
                
                axios.get(api + 'shops/' + this.$route.params.id, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('token')
                    }
                })
                .then(response => {
                    this.shop.name = response.data.info.name
                    this.shop.id = response.data.info.id
                    this.shop.coupons = response.data.data
                });
            },
            deleteShop() {
                axios.delete(api + 'shops/' + this.shop.id, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('token')
                    }
                })
                .then(response => {
                    this.$router.push('/');
                });
            },
        },
        created() {
            if (this.$store.state.user == null) {
                this.$router.push('/login')
            } else {
                this.load();
            }
        }
    };

</script>
<style scoped>

</style>