import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type Type = {
	check: boolean;
	totalNum: number;
};

export const initialType: Type = {
	check: false,
	totalNum: 0,
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
		updatePageTotalNum: (state, action: PayloadAction<number>) => {
			const type = state;
			type.totalNum = action.payload;
			return state;
		}
	},
});

export const { updateKeywords, updatePageTotalNum } = updateKeywordsSlice.actions;

export default updateKeywordsSlice.reducer;
