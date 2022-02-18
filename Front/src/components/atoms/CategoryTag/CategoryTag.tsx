import React from "react";
import styled from "@emotion/styled";

interface categoryTagType {
	text: string;
}

const CategoryTag: React.FC<categoryTagType> = ({ text }) => {
	return <CategoryWrap>{text}</CategoryWrap>;
};

const CategoryWrap = styled.div`
	cursor: pointer;
	border: 1px solid transparent;
	font-weight: 500;
	transition: all 0.3s ease;
	border-radius: 0.25rem;
	font-size: 0.875rem;
	line-height: 1.25rem;
	padding: 0.3125rem 1rem;
	border-color: #55b689;
	background-color: #55b689;
	color: #fff;
	&:hover {
		border-color: #4a8167;
		background-color: #4a8167;
		color: #fff;
	}
`;

export default CategoryTag;
