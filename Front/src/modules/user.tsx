import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import axios from "axios";
import { deleteAccessToken, getAccessToken } from "../helpers/tokenControl";
import {
	deleteUserID,
	deleteEmail,
	deleteName,
	deletePhone,
	deleteRole,
} from "../helpers/userInfoControl";

const LOGIN_URL = "http://54.180.134.20/api"; // http 붙여야함 (404 오류 방지)

function CHECK_FAILURE() {
	try {
		localStorage.removeItem("email"); // localStorage에서 email(ID) 제거
	} catch (e) {
		console.log("localStorage is not working");
	}
}

function LOGOUT() {
	try {
		/* logout api 호출 */
		axios
			.get(`${LOGIN_URL}/logout`, {
				headers: {
					Authorization: `Bearer ${getAccessToken()}`,
				},
			})
			.then((res) => console.log(res))
			.catch((e) => console.error(e));

		// localStorage에서 user 정보 제거
		deleteAccessToken();
		deleteUserID();
		deleteEmail();
		deleteName();
		deletePhone();
		deleteRole();
	} catch (e) {
		console.log("localStorage is not working");
	}
}

interface State {
	id: string;
	email: string | null;
	name: string | null;
	phone: string | null;
	role: string | null;
	checkError: string;
}

const initialState: State = {
	id: "",
	email: "",
	name: "",
	phone: "",
	role: "",
	checkError: "",
};

// action & reducer를 동시 만듦
const userSlice = createSlice({
	name: "userActions", // A name, used in action types
	initialState, // The initial state for the reducer
	reducers: {
		// reducer
		tempSetID: (state, action: PayloadAction<string>) => {
			const type = state; /* no-param-reassign 에러 */
			type.id = action.payload;
		},
		tempSetEmail: (state, action: PayloadAction<string | null>) => {
			const type = state;
			type.email = action.payload;
		},
		tempSetName: (state, action: PayloadAction<string | null>) => {
			const type = state;
			type.name = action.payload;
		},
		tempSetPhone: (state, action: PayloadAction<string | null>) => {
			const type = state;
			type.phone = action.payload;
		},
		tempSetRole: (state, action: PayloadAction<string | null>) => {
			const type = state;
			type.role = action.payload;
		},
		checkSuccess: (state, action: PayloadAction<string>) => {
			const type = state;
			type.id = action.payload;
			type.checkError = "";
		},
		checkFailure: (state, action: PayloadAction<string>) => {
			const type = state;
			type.id = "";
			type.checkError = action.payload;
			CHECK_FAILURE();
		},
		logout: (state) => {
			const type = state;
			type.id = "";
			type.email = "";
			type.name = "";
			type.phone = "";
			type.role = "";
			type.checkError = "";
			LOGOUT();
		},
	},
});

// export const authSlice.actions;
export const {
	tempSetID,
	tempSetEmail,
	tempSetName,
	tempSetPhone,
	tempSetRole,
	checkSuccess,
	checkFailure,
	logout,
} = userSlice.actions;
export default userSlice.reducer;
