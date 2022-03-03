import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type Type = {
	check: boolean;
};

export const initialType: Type = {
	check: false,
};

export const updateKeywordsSlice = createSlice({
	name: "selectMenuItems",
	initialState: initialType,
	reducers: {
		updateKeywords: (state, action: PayloadAction<boolean>) => {
			const type = state;
			type.check = action.payload;
			return state;
		},
	},
});

export const { updateKeywords } = updateKeywordsSlice.actions;

export default updateKeywordsSlice.reducer;
