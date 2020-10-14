<template>
    <div class="signup-form">
        <form>
            <h3>Sign Un</h3>

            <div class="signup-box">
                <label>Name</label>
                <input type="text"/>
            </div>

            <div class="signup-box">
                <label>Email address</label>
                <input type="email"/>
            </div>

            <div class="signup-box">
                <label>Password</label>
                <input type="password"/>
            </div>

            <button type="submit">Sign Up</button>
        </form>
        <br/>
    </div>
</template>

<script>
    export default {
        data() {
            return {}
        }
    }
</script>