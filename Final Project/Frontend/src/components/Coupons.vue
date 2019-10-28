<template>
<div class='container'>
    <h1>Coupons</h1>

    <input type='checkbox' v-model='active' v-on:change='load()'>
    <label v-if='active'>Active Coupons</label><label v-else>All Coupons</label>
    <br>

    <table v-if='coupons.length > 0'>
        <thead>
            <tr>
                <th scope='col'>Product Name</th>
                <th scope='col'>Shop</th>
                <th scope='col'>Discounted Price</th>
                <th scope='col'>Original Price</th>
                <th scope='col'>Sale</th>
                <th scope='col'>Valid From</th>
                <th scope='col'>Valid To</th>
                <th scope='col'>Action</th>
            </tr>
        </thead>
        <tbody>
            <tr v-bind:key='coupon.id' v-for='coupon in coupons'>
                <td>{{ coupon.product }}</td>
                <td>
                    <router-link :to="'/shop/' + coupon.shop.id">{{ coupon.shop.name }}</router-link>
                </td>
                <td>{{ coupon.discountedPrice }}</td>
                <td>{{ coupon.originalPrice }}</td>
                <td>{{ coupon.sale }}</td>
                <td>{{ (coupon.validFrom != null)?coupon.validFrom.split('T')[0]:'N/A'  }}</td>
                <td>{{ (coupon.validTo != null)?coupon.validTo.split('T')[0]:'N/A' }}</td>
                <td><button v-on:click='deleteCoupon(coupon.id)'>Delete</button></td>
            </tr>
        </tbody>
    </table>
    <div v-if='coupons.length===0'>No coupons available.</div>
    <br>

    <paginate class='mx-auto' v-if='coupons.length > 0'
        v-model='current'
        :page-count='pages'
        :click-handler='load'
        :prev-text='"Prev"'
        :next-text='"Next"'
        :container-class="'pagination'"
        :page-class="'page-item'"
        :page-link-class="'page-link'"
        :prev-class="'page-item'"
        :prev-link-class="'page-link'"
        :next-class="'page-item'"
        :next-link-class="'page-link'"   
    ></paginate>
    
</div>
</template>
<script>
    import {api} from '../main';
    import axios from 'axios';
    import Paginate from 'vuejs-paginate';

    export default {
        name: 'Coupons',

        components: {Paginate},

        data() {
            return {
                pages: 1,
                current: 1,
                coupons: [],
                active: false,
            };
        },
        computed: {

        },
        methods: {
            load(page = this.current) {
                this.current = page;
                axios.get(api + 'coupons', {
                    params: {
                        page: page,
                        active: this.active
                    },
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('token')
                    }
                })
                .then(response => {
                    this.pages = response.data.info.pages;
                    this.coupons = response.data.data;
                });
            },
            deleteCoupon(id) {
                axios.delete(api + 'coupons/' + id, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('token')
                    }
                })
                .then(() => {
                    this.load()
                });
            }
        },
        created() {
            if (this.$store.state.user == null) {
                this.$router.push('/login')
            } else {
                this.load(1);
            }
        }
    };

</script>
<style scoped>

</style>