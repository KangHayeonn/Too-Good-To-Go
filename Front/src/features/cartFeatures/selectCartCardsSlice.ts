// rtk
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { shopData } from "../../components/molecules/ShopDummyData";
// localStorage helper function
import { setLocalStorageCart } from "../../helpers/cartControl";

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
	cartItemQuantity: number;
};

const initialCards: initialCartCardType[] = shopData.map((e: CartCardType) => {
	return { ...e, isChecked: false, cartItemQuantity: 1 };
});

console.log("reducer");

export const selectCartCardsSlice = createSlice({
	name: "selectCartCards",
	initialState: initialCards,
	reducers: {
		selectCartCardByID: (state, action: PayloadAction<number>) => {
			const cards = state.map((e) => {
				if (e.shopId === action.payload) {
					return { ...e, isChecked: !e.isChecked };
				}
				return e;
			});
			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(cards));

			return cards;
		},
		selectAllCartCards: (state, action: PayloadAction<boolean>) => {
			if (action.payload === true) {
				const cards = state.map((e) => {
					return { ...e, isChecked: false };
				});
				// set localstorage with state.selectCartCards
				setLocalStorageCart(JSON.stringify(cards));

				return cards;
				// eslint-disable-next-line no-else-return
			} else if (action.payload === false) {
				const cards = state.map((e) => {
					return { ...e, isChecked: true };
				});
				// set localstorage with state.selectCartCards
				setLocalStorageCart(JSON.stringify(cards));

				return cards;
			} else {
				return state;
			}
		},
		deleteSelectedCards: (state) => {
			let filteredArrOfState: initialCartCardType[] = [];

			filteredArrOfState = state.filter((e) => {
				return e.isChecked === false;
			});
			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(filteredArrOfState));

			return filteredArrOfState;
		},
		incrementSelectedCards: (state, action: PayloadAction<number>) => {
			const cards = state.map((e) => {
				if (e.shopId === action.payload) {
					return { ...e, cartItemQuantity: e.cartItemQuantity + 1 };
				}
				return e;
			});

			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(cards));

			return cards;
		},
		decrementSelectedCards: (state, action: PayloadAction<number>) => {
			// Deletes card which has cartItemQuantity <= 0
			const cards = state
				.map((e) => {
					if (e.shopId === action.payload) {
						return {
							...e,
							cartItemQuantity: e.cartItemQuantity - 1,
						};
					}
					return e;
				})
				.filter((e) => {
					return e.cartItemQuantity;
				});

			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(cards));

			return cards;
		},
		addItemsToCart: (
			state,
			action: PayloadAction<initialCartCardType[]>
		) => {
			console.log("addItemsToCart:", action.payload);

			// set localstorage with state.selectCartCards
			setLocalStorageCart(JSON.stringify(action.payload));

			return action.payload;
		},
	},
});

// Action creators are generated for each case reducer function
export const {
	selectCartCardByID,
	selectAllCartCards,
	deleteSelectedCards,
	incrementSelectedCards,
	decrementSelectedCards,
	addItemsToCart,
} = selectCartCardsSlice.actions;

export default selectCartCardsSlice.reducer;
