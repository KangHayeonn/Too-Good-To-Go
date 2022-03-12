import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export const updatePriority = createSlice({
	name: "updateProductBoolean",
	initialState: Boolean,
	reducers: {
		updatePriorities: (state, action: PayloadAction<boolean>) => {
			return action.payload;
		},
	},
});

export const { updatePriorities } = updatePriority.actions;

export default updatePriority.reducer;
