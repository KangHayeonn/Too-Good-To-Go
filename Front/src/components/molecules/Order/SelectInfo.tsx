/* eslint-disable react/jsx-props-no-spreading */
import React, { useEffect } from "react";
import styled from "@emotion/styled/macro";
import { useDispatch } from "react-redux";
import { setOrderInfoCheck } from "../../../features/order/orderInfoSlice";

const Select = [
	{
		name: "주문자 정보와 동일",
		value: "USER",
		default: true,
		hex: "#ffb598",
	},
	{ name: "새로운 정보 입력", value: "NEW", hex: "#c2dbff" },
];

const ColorSelectorContainer = styled.div`
	display: flex;
	justify-content: flex-start;
	width: 95%;
	margin: 2em 1em 29px 1em;
	border-bottom: 1px solid #eee;
	padding-bottom: 1em;
`;

const Label = styled.label`
	display: inline-block;
	margin-top: 4px;
	margin-right: 13px;
	padding: 7px;
`;

const RadioButton = styled.input`
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	vertical-align: middle;
	width: 1.27em;
	height: 1.27em;
	border: 2px solid #cfcfcf;
	border-radius: 50%;
	&:checked + ${Label} {
		// Label부분 때문에 macro 써야함
		color: no-repeat ${(props) => props.color};
	}
	&:checked {
		width: 1.24em;
		height: 1.24em;
		border: 3px solid #ffffff;
		background: #4f4f4f;
		box-shadow: 0 0 0 1px #4f4f4f;
	}
`;

const SelectInfo: React.FC = () => {
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(setOrderInfoCheck("USER"));
	}, []);

	return (
		<>
			<ColorSelectorContainer>
				{Select.map((row) => (
					<div key={row.name}>
						<RadioButton
							id={row.name}
							type="radio"
							name="select-info"
							value={row.value}
							defaultChecked={row.default}
							onClick={() =>
								dispatch(setOrderInfoCheck(row.value))
							}
						/>
						<Label htmlFor={row.name} color={row.hex}>
							{row.name}
						</Label>
					</div>
				))}
			</ColorSelectorContainer>
		</>
	);
};
export default SelectInfo;
