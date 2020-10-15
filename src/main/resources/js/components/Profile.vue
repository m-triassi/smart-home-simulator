<template>

    <div>

        <table class="profile_table">

            <tr>
                <button>ON</button>
            </tr>

            <tr>
                <button>Edit</button>
            </tr>

            <tr>
                <img src="\\main\\resources\\static\\assets\\default-profile.png" alt="Default Profile"/>
            </tr>
                
            <tr>
                <h5>Location:</h5>
                <h5>Kitchen</h5>
            </tr>
                
            <tr>
                <h5><strong>Outside Temp. 15C</strong></h5>
            </tr>

            <tr>
                <h5><strong>Date</strong></h5>
            </tr>

        </table>

    </div>

</template>

<script>

export default {
        name: 'profile',
        data() {
            return {
            };
        }
}

</script>

<style>

    .profile_table{
        width: 100%;
        height: 100%;
    }

</style>