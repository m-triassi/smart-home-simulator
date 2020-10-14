<template>
    <div class="login-form">
        <form>
            <h3>Sign In</h3>

            <div class="login-box">
                <label>Email address</label>
                <input type="email"/>
            </div>

            <div class="login-box">
                <label>Password</label>
                <input type="password"/>
            </div>

            <button type="submit">Sign In</button>
        </form>
        <br/>
    </div>
</template>

<script>
    export default {
        name : 'login',
        data() {
            return {}
        }
    }
</script>