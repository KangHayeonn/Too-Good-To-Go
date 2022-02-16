import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface State {
	email: string;
	password: string;
}

const initialState: State = {
	email: "",
	password: "",
};

// action & reducer를 동시 만듦
const authSlice = createSlice({
	name: "authActions", // A name, used in action types
	initialState, // The initial state for the reducer
	reducers: {
		// reducer
		changeIdItem: (state, action: PayloadAction<string>) => {
			const type = state; /* no-param-reassign 에러 */
			type.email = action.payload;
		},
		changePwItem: (state, action: PayloadAction<string>) => {
			const type = state;
			type.password = action.payload;
		},
		initializeForm: (state) => {
			const type = state;
			type.email = "";
			type.password = "";
		},
	},
});

// export const authSlice.actions;
export const { changeIdItem, changePwItem, initializeForm } = authSlice.actions;
export default authSlice.reducer;
