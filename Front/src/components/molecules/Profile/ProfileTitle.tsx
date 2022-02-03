/* eslint-disable react/no-unescaped-entities */
import React from "react";
import styled from "@emotion/styled";
import { useSelector } from "react-redux";
import { RootState } from "../../../app/store";

const ProfileTitle: React.FC = () => {
	const userName = useSelector((state: RootState) => {
		return state.user.user;
	});

	return (
		<Title>
			<p>
				안녕하세요,
				<span>
					{userName ? `${userName}` : `이렇게 들어오시면 안되는데요`}
				</span>
				님
			</p>
		</Title>
	);
};

export default ProfileTitle;

const Title = styled.div`
	width: 907px;
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
