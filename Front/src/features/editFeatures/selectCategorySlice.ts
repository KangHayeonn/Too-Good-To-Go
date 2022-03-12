import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type initialBtnType = CategoryCardType & {
	isChecked: boolean;
};

export type CategoryCardType = {
	categoryName: string;
};

const initialCategoryBtn: initialBtnType[] = [
	{
		categoryName: "한식",
		isChecked: false,
	},
	{
		categoryName: "분식",
		isChecked: false,
	},
	{
		categoryName: "야식",
		isChecked: false,
	},
	{
		categoryName: "양식",
		isChecked: false,
	},
	{
		categoryName: "일식",
		isChecked: false,
	},
	{
		categoryName: "중식",
		isChecked: false,
	},
	{
		categoryName: "패스트푸드",
		isChecked: false,
	},
	{
		categoryName: "치킨",
		isChecked: false,
	},
	{
		categoryName: "피자",
		isChecked: false,
	},
	{
		categoryName: "찜탕",
		isChecked: false,
	},
	{
		categoryName: "디저트",
		isChecked: false,
	},
];

export const selectCategorySlice = createSlice({
	name: "selectCategory",
	initialState: initialCategoryBtn,
	reducers: {
		selectCategory: (state, action: PayloadAction<string>) => {
			const cards = state.map((e) => {
				if (e.categoryName === action.payload) {
					return { ...e, isChecked: !e.isChecked };
				}
				return e;
			});
			return cards;
		},
		reset: () => initialCategoryBtn,
	},
});

export const { selectCategory, reset } = selectCategorySlice.actions;

export default selectCategorySlice.reducer;
