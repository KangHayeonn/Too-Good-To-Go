import { createSlice, PayloadAction } from "@reduxjs/toolkit";

function CHECK_FAILURE() {
    try {
        localStorage.removeItem("email"); // localStorage에서 email(ID) 제거
    } catch(e) {
        console.log("localStorage is not working");
    }
}

function LOGOUT() {
    try {
        /* logout api 호출 */
        localStorage.removeItem("email"); // localStorage에서 email(ID) 제거
    } catch(e) {
        console.log("localStorage is not working");
    }
}

/* 구현 예정
function user() {

    // check
    // if, checkfailure
    // -> logout
}
*/

interface State {
    user: string,
    checkError: string
};

const initialState: State = {
    user : "",
    checkError: "",
};

// action & reducer를 동시 만듦
const userSlice = createSlice({
    name: "userActions", // A name, used in action types
    initialState, // The initial state for the reducer
    reducers: { // reducer
        tempSetUser : (state, action: PayloadAction<string>) => {
            const type = state; /* no-param-reassign 에러 */
            type.user = action.payload; 
        }, 
        checkSuccess: (state, action: PayloadAction<string>) => {
            const type = state;
            type.user = action.payload;
            type.checkError = "";
        },
        checkFailure: (state, action: PayloadAction<string>) => {
            const type = state;
            type.user = "";
            type.checkError = action.payload;
            CHECK_FAILURE();
        },
        logout: (state) => {
            const type = state;
            type.user = "";
            LOGOUT();
        },
    },
});

// export const authSlice.actions;
export const { tempSetUser, checkSuccess, checkFailure, logout } = userSlice.actions;
export default userSlice.reducer;