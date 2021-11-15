// rtk
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { shopData } from "../../components/molecules/ShopDummyData";

export type CartCardType = {
	shopId: number;
	shopFoodImg: string;
	shopName: string;
	shopFoodName: string;
	shopFoodSale: number;
	shopFoodCost: number;
	shopBeforeCost: number;
};

export type initialCartCardType = CartCardType & {
	isChecked: boolean;
};

export const initialCards: initialCartCardType[] = shopData.map(
	(e: CartCardType) => {
		return { ...e, isChecked: false };
	}
);

export const selectCartCardsSlice = createSlice({
	name: "selectCartCards",
	initialState: initialCards,
	reducers: {
		selectCartCardByID: (state, action: PayloadAction<number>) => {
			return state.map((e) => {
				if (e.shopId === action.payload) {
					return { ...e, isChecked: !e.isChecked };
				}
				return e;
			});
		},
		selectAllCartCards: (state, action: PayloadAction<boolean>) => {
			if (action.payload === true) {
				return state.map((e) => {
					return { ...e, isChecked: false };
				});
				// eslint-disable-next-line no-else-return
			} else if (action.payload === false) {
				return state.map((e) => {
					return { ...e, isChecked: true };
				});
			} else {
				return state;
			}
		},
		deleteSelectedCards: (state) => {
			let filteredArrOfState: initialCartCardType[] = [];
			console.log("before filter ", state);
			filteredArrOfState = state.filter((e) => {
				return e.isChecked === false;
			});

			console.log("after filter ", filteredArrOfState);

			return filteredArrOfState;
		},
	},
});

// Action creators are generated for each case reducer function
export const { selectCartCardByID, selectAllCartCards, deleteSelectedCards } =
	selectCartCardsSlice.actions;

export default selectCartCardsSlice.reducer;
