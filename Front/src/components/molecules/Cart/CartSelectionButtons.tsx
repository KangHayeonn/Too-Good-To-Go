import React from "react";
import styled from "@emotion/styled";
import Checkbox from "@mui/material/Checkbox";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Button from "@mui/material/Button";

const label = { inputProps: { "aria-label": "Checkbox demo" } };

const CartSelectionButtons = () => {
	return (
		<Wrapper>
			<FormGroup>
				<FormControlLabel control={<Checkbox />} label="전체 선택" />
			</FormGroup>
			<Button variant="contained">삭제하기</Button>
		</Wrapper>
	);
};

export default CartSelectionButtons;

const Wrapper = styled.div`
	display: flex;
	width: 670px;
	height: 50px;
	border: 1px solid black;
	justify-content: space-between;
	margin-top: 13px;
`;
