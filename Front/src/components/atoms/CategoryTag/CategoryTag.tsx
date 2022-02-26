import React from "react";
import styled from "@emotion/styled";

interface categoryTagType {
	text: string;
	onClick: () => void;
	isCheck: boolean;
}

type theme = {
	checked: boolean;
};

const CategoryTag: React.FC<categoryTagType> = ({ text, onClick, isCheck }) => {
	return (
		<CategoryWrap type="button" onClick={onClick} checked={isCheck}>
			{text}
		</CategoryWrap>
	);
};

const CategoryWrap = styled.button<theme>`
	border: 1px solid transparent;
	font-weight: 500;
	transition: all 0.2s ease;
	border-radius: 0.25rem;
	font-size: 0.875rem;
	padding: 0.3125rem 1rem;
	border-color: ${({ checked }) => (checked ? `#469972` : `#55b689`)};
	background-color: ${({ checked }) => (checked ? `#469972` : `#55b689`)};
	color: #fff;
	&:hover {
		border-color: #469972;
		background-color: #469972;
	}
	${({ checked }) =>
		checked && `box-shadow: 2px 2px 2px 1px rgba(84, 182, 137, 0.3)`}
`;

export default CategoryTag;
