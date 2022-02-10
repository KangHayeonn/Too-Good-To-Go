import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export const updateMenuItemsSlice = createSlice({
	name: "selectMenuItems",
	initialState: [],
	reducers: {
		updateMenuItems: (state, action: PayloadAction<[]>) => {
			return action.payload;
		},
	},
});

export const { updateMenuItems } = updateMenuItemsSlice.actions;

export default updateMenuItemsSlice.reducer;
