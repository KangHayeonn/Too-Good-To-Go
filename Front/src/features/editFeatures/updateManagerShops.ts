import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export const updateManagerShops = createSlice({
	name: "selectMenuTitle",
	initialState: [],
	reducers: {
		updateManagerShop: (state, action: PayloadAction<[]>) => {
			return action.payload;
		},
	},
});

export const { updateManagerShop } = updateManagerShops.actions;

export default updateManagerShops.reducer;
