import { createStore } from 'vuex'

export default createStore({
    state: {
        person: {
            id: 'person_id',
            name: 'person_name'
        },
        user: {
            ID_number: ''
        },
        customer: {
            id: 'a',
            name: 'b',
            identification: 'c',
            address: 'd',
            phoneNumber: 'e'
        },
    },
    mutations: {},
    actions: {},
    modules: {}
})