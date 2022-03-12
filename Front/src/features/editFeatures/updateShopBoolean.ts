import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export const updateShopBoolean = createSlice({
	name: "updateShopBoolean",
	initialState: Boolean,
	reducers: {
		updateShopsBoolean: (state, action: PayloadAction<boolean>) => {
			return action.payload;
		},
	},
});

export const { updateShopsBoolean } = updateShopBoolean.actions;

export default updateShopBoolean.reducer;
