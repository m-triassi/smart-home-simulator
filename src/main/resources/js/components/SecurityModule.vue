<template>
    <div>
        <table class="module_table">
            <tr>
                <p>This is the security module</p>
            </tr>
            <tr>
                <div>
                    <toggle-button
                        :sync="true"
                        :value="isArmed"
                        :labels="{ checked: 'Armed', unchecked: 'Not Armed' }"
                        @change="changeState()"
                        :width="100"
                        :color="{
                            checked: '#900505',
                            unchecked: '#08F208',
                        }"
                    />
                </div>
            </tr>
        </table>
    </div>
</template>

<script>
export default {
    name: 'security',
    data() {
        return {
            zonesList: {},
            homeResponse: null,
            isArmed:
                this.$store.state.user.home.securityLevel == 'armed'
                    ? true
                    : false,
        };
    },
    methods: {
        async changeState() {
            if (this.$store.state.user.home.securityLevel == 'unarmed') {
                this.homeResponse = await axios
                    .post(
                        '/home/update?id=' +
                            this.$store.state.user.home.id +
                            '&security_level=armed'
                    )
                    .then(function (response) {
                        return response;
                    })
                    .catch(function (error) {
                        console.log(error);
                    });

                if (this.homeResponse.data.message == '') {
                    this.$store.commit(
                        'appendMessage',
                        'Success. House is [Armed]'
                    );
                    this.$store.state.user.home.securityLevel = 'armed';
                    this.isArmed = true;
                } else {
                    this.$store.commit(
                        'appendMessage',
                        this.homeResponse.data.message
                    );
                }
            } else if (this.$store.state.user.home.securityLevel == 'armed') {
                this.homeResponse = await axios
                    .post(
                        '/home/update?id=' +
                            this.$store.state.user.home.id +
                            '&security_level=unarmed'
                    )
                    .then(function (response) {
                        return response;
                    })
                    .catch(function (error) {
                        console.log(error);
                    });

                if (this.homeResponse.data.message == '') {
                    this.$store.commit(
                        'appendMessage',
                        'Success. House is [Unarmed]'
                    );
                    this.$store.state.user.home.securityLevel = 'unarmed';
                    this.isArmed = false;
                } else {
                    this.$store.commit(
                        'appendMessage',
                        this.homeResponse.data.message
                    );
                }
            }
        },
        callAxios(path) {
            axios
                .post(path)
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        checkSecurityState() {
            if (this.$store.state.user.home.securityLevel == null) {
                this.$store.state.user.home.securityLevel = 'unarmed';
            }
        },
    },
    mounted() {
        setTimeout(this.checkSecurityState(), 1000);
    },
};
</script>

<style>
table,
tr,
td {
    border: 1px solid black;
    border-collapse: collapse;
}

.module_table {
    width: 50%;
    height: auto;
}
</style>