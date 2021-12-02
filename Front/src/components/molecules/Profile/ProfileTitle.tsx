/* eslint-disable react/no-unescaped-entities */
import React from "react";
import styled from "@emotion/styled";

const ProfileTitle: React.FC = () => {
	return (
		<Title>
			<p>
				안녕하세요, <span> '$[name]'</span>님!
			</p>
		</Title>
	);
};

export default ProfileTitle;

const Title = styled.div`
	width: 1120px;
	height: 127px;
	background: #ffffff;
	border: 1px solid #646464;
	display: flex;
	flex-direction: row;
	justify-content: flex-start;
	align-items: center;
	margin-top: 150px;
	p {
		position: relative;
		left: 35px;
		font-size: 32px;

		span {
			color: #58c656;
		}
	}
`;
