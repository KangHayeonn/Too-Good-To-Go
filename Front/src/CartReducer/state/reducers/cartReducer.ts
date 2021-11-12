import { shopData } from "../../../components/molecules/ShopDummyData";
import { ShopsType } from "../../../components/molecules/Cart/CartCards";
import { Action } from "../actions";
import { ActionType } from "../action-types";

export type NewShopsType = ShopsType & {
	isChecked: boolean;
};

const initialState: NewShopsType[] = shopData.map((data) => {
	return { ...data, isChecked: false };
});

console.log("initialState :", initialState);

const reducer = (state: NewShopsType[] = initialState, action: Action) => {
	switch (action.type) {
		case ActionType.CHECK:
			return state.map((e, i) => {
				// returns matching object's isChecked to flip
				if (e.shopId === action.payload.id) {
					return { ...e, isChecked: !e.isChecked };
				}
				return e;
			});

		default:
			return state;
	}
};

export default reducer;
