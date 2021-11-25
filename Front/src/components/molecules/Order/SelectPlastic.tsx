/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled/macro";

const Select = [
	{ name: "일회용 수저, 포크 안주셔도 돼요. 🌱", hex: "#54b689" },
	{ name: "일회용 수저, 포크 넣어주세요.", hex: "#646464" },
];

const ColorSelectorContainer = styled.div`
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	width: 95%;
	margin: 1em 1em 29px 1em;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
	padding: 1em;
`;

const Label = styled.label`
	display: inline-block;
	margin-top: 4px;
	padding: 7px;
	color: ${(props) => props.color};
`;

const RadioButton = styled.input`
	vertical-align: middle;
	width: 1.27em;
	height: 1.27em;
	background: red;
	&:checked + ${Label} {
		color: no-repeat ${(props) => props.color};
	}
`;

const SelectPlastic: React.FC = () => {
	return (
		<>
			<ColorSelectorContainer>
				{Select.map((row) => (
					<div key={row.name}>
						<RadioButton
							id={row.name}
							type="radio"
							name="select-info"
							value={row.name}
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
export default SelectPlastic;
