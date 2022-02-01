import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface State {
    email : string
    password: string
};

const initialState: State = {
    email : "",
    password: "",
};

/*
type State = authType;
export const changeField: CaseReducer<State, PayloadAction<string>> = (state, action) => {
    state.push(action.payload);
    console.log(action);
    console.log(state.login.email);
    console.log(state.login.password);
    type.login.email = action.payload.email;

};

export const initializeForm: CaseReducer<State>  = (state) => {
    console.log(initialState.login.email);
    console.log(initialState.login.password);
};

export const authSlice = createSlice({
    name: "authActions",
    initialState,
    reducers: {
        changeField,
        initializeForm,
    },
})

console.log(authSlice.actions);
*/

// action & reducer를 동시 만듦
const authSlice = createSlice({
    name: "authActions", // A name, used in action types
    initialState, // The initial state for the reducer
    reducers: { // reducer
        changeField : (state, action: PayloadAction<{ email : string; password: string; }>) => {
            const { email, password } = action.payload;
            // state.login.push({ email, password });
            const type = state; /* no-param-reassign 에러 */
            type.email = email; 
            type.password = password;
        }, // action 생성
        initializeForm: (state) => {
            const type = state;
            type.email = "";
            type.password = "";
        },
    },
});

// export const authSlice.actions;
export const { changeField, initializeForm } = authSlice.actions;
export default authSlice.reducer;