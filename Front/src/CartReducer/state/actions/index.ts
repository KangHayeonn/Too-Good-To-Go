import { ActionType } from "../action-types";

// type InitialStateType = ShopsType & {
// 	isEdit: boolean;
// };

export type Action = {
	type: ActionType.CHECK;
	payload: {
		id: number;
	};
};
