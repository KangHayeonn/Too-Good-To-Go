import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import Checkbox from "@mui/material/Checkbox";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Button from "@mui/material/Button";
// rtk
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../app/store";
import { selectAllCartCards } from "../../../features/cartFeatures/selectCartCardsSlice";

const CartSelectionButtons: React.FC = () => {
	const [selectAll, setSelectAll] = useState<boolean>(false);

	// const isCheckedArr = useSelector((state: RootState) => {
	// 	return state.selectCartCards.
	// })

	function handleSelectAll(e: React.MouseEvent<HTMLButtonElement>) {
		e.target as HTMLButtonElement;
		dispatch(selectAllCartCards(selectAll));
		setSelectAll(!selectAll);
	}

	// Select All button logic.
	const isCardsChecked: boolean = useSelector((state: RootState) => {
		return state.selectCartCards.some((e) => {
			return e.isChecked === false;
		});
	});

	useEffect(() => {
		if (!isCardsChecked) {
			return setSelectAll(true);
		}
		return setSelectAll(false);
	}, [isCardsChecked]);

	const dispatch = useDispatch();

	return (
		<Wrapper>
			<FormGroup>
				<FormControlLabel
					control={
						<Checkbox
							onClick={(e) => {
								handleSelectAll(e);
							}}
							checked={selectAll}
						/>
					}
					label="전체 선택"
				/>
			</FormGroup>
			<Button variant="contained" size="small">
				삭제하기
			</Button>
		</Wrapper>
	);
};

export default CartSelectionButtons;

const Wrapper = styled.div`
	display: flex;
	width: 670px;
	height: 50px;
	/* border: 1px solid black; */
	justify-content: space-between;
	margin-top: 13px;
	margin-bottom: 8px;
`;
