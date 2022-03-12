import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export const updateManagerShops = createSlice({
	name: "selectMenuTitle",
	initialState: Boolean,
	reducers: {
		updateManagerShop: (state, action: PayloadAction<boolean>) => {
			return action.payload;
		},
	},
});

export const { updateManagerShop } = updateManagerShops.actions;

export default updateManagerShops.reducer;
