import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export const updateMenuTitleSlice = createSlice({
	name: "selectMenuTitle",
	initialState: [],
	reducers: {
		updateMenuTitle: (state, action: PayloadAction<[]>) => {
			return action.payload;
		},
	},
});

export const { updateMenuTitle } = updateMenuTitleSlice.actions;

export default updateMenuTitleSlice.reducer;
