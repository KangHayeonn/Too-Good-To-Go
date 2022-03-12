import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export const updateProductBoolean = createSlice({
	name: "updateProductBoolean",
	initialState: Boolean,
	reducers: {
		updateProductsBoolean: (state, action: PayloadAction<boolean>) => {
			return action.payload;
		},
	},
});

export const { updateProductsBoolean } = updateProductBoolean.actions;

export default updateProductBoolean.reducer;
