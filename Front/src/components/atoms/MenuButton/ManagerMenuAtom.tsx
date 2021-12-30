import React from "react";
import { css } from "@emotion/react";
import styled from "@emotion/styled";

const menuAtomBtn = css`
	width: 200px; //상단 메뉴바 크기 안맞아서 수정
	height: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	border-collapse: collapse;

	box-shadow: -1px 0 0 0 #555 inset;
	color: #fff;
	font-size: 15px;
	font-weight: 400;
`;

const Li = styled.li`
	display: flex;
	flex-direction: column;
`;

type menuType = {
	menuName: string;
};

const ManagerMenuAtom: React.FC<menuType> = ({ menuName }) => {
	return (
		<Li>
			<div css={menuAtomBtn}>{menuName}</div>
		</Li>
	);
};

export default ManagerMenuAtom;
