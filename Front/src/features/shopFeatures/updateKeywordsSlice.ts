import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type Type = {
	check: boolean;
	totalNum: number;
	keyword: string;
};

export const initialType: Type = {
	check: false,
	totalNum: 0,
	keyword: "",
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
		},
		updateKeyword: (state, action: PayloadAction<string>) => {
			const type = state;
			type.keyword = action.payload;
			return state;
		}
	},
});

export const { updateKeywords, updatePageTotalNum, updateKeyword } = updateKeywordsSlice.actions;

export default updateKeywordsSlice.reducer;
