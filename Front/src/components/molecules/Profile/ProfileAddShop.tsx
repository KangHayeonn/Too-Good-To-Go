import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
// MUI
import Button from "@mui/material/Button";
import Line13 from "../../../../public/image/Line 13.png";
import CategoryTag from "../../atoms/CategoryTag/CategoryTag";
import {
	initialBtnType,
	reset,
	selectCategory,
} from "../../../features/editFeatures/selectCategorySlice";
import { RootState } from "../../../app/store";
import { getAccessToken } from "../../../helpers/tokenControl";
import { updateManagerShop } from "../../../features/editFeatures/updateManagerShops";

const ProfileAddShop: React.FC = () => {
	const [shopInfo, setShopInfo] = useState({});
	const [image, setImage] = useState<File>();
	const formData = new FormData();

	const productPost = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { name, value } = e.target;
		setShopInfo({
			...shopInfo,
			[name]: value,
		});
	};

	const onSaveFiles = (e: React.ChangeEvent<HTMLInputElement>) => {
		if (e.target.files) {
			const file: FileList = e.target.files;
			const uploadFile = file[0];
			setImage(uploadFile);
		}
	};
	const [categoryArr, setCategoryArr] = useState<initialBtnType[]>([]);
	const dispatch = useDispatch();
	const reduxStateCollector = useSelector((state: RootState) => {
		return state.selectCategory;
	});

	const shopCategoryChange = (ca: initialBtnType[]): void => {
		const newCategoryArr = ca.filter((el) => {
			return el.isChecked === true;
		});
		const returnCategory = newCategoryArr.map((el) => {
			return el.categoryName;
		});
		setShopInfo({
			...shopInfo,
			category: returnCategory,
		});
	};

	useEffect(() => {
		setCategoryArr(reduxStateCollector);
		shopCategoryChange(reduxStateCollector);
	}, [reduxStateCollector]);

	useEffect(() => {
		return () => {
			dispatch(reset());
		};
	}, []);

	const SHOP_API_URL = `http://54.180.134.20/api/manager/shops`;
	const PostShopInfo = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
				"Content-Type": "multipart/form-data",
			},
		};
		return axios.post(SHOP_API_URL, formData, config);
	};
	const appendFormData = () => {
		const dtoObj = new Blob([JSON.stringify(shopInfo)], {
			type: "application/json",
		});
		formData.set("request", dtoObj);

		if (image) {
			formData.append("file", image);
		} else {
			const imageJson = new File([], "");
			formData.set("file", imageJson);
		}
	};
	const displayShops = useSelector((state: RootState) => {
		return state.updateManagerShops;
	});
	const post = () => {
		appendFormData();

		PostShopInfo().then(
			() => {
				console.log("post success");
				alert("가게가 등록되었습니다."); // eslint-disable-line no-alert
				setShopInfo({});
			},
			() => {
				console.log("post fail");
				console.log(shopInfo);
			}
		);

		if (displayShops) {
			dispatch(updateManagerShop(false));
		} else {
			dispatch(updateManagerShop(true));
		}
	};
	return (
		<FormWrapper>
			<EditTitle className="edit-title">
				<p>매점 관리</p>
				<img src={Line13} alt="line13" />
				<p>SHOP</p>
			</EditTitle>
			<Wrapper>
				<div className="form-wrapper">
					<div className="section-wrapper">
						<div className="profile-info">가게 이름</div>
						<InputStyle
							name="name"
							type="text"
							placeholder="가게 이름"
							onChange={productPost}
						/>
					</div>
					<div className="section-wrapper">
						<div className="profile-info">가게 이미지</div>
						<InputStyle
							name="image"
							type="file"
							accept="image/*"
							placeholder="상품 이미지"
							onChange={onSaveFiles}
						/>
					</div>

					<div className="section-wrapper category">
						<div className="profile-info">카테고리</div>
						<ul className="checkboxDiv">
							{categoryArr.map((card: initialBtnType) => {
								return (
									<li key={card.categoryName}>
										<CategoryTag
											text={card.categoryName}
											onClick={() => {
												dispatch(
													selectCategory(
														card.categoryName
													)
												);
											}}
											isCheck={card.isChecked}
										/>
									</li>
								);
							})}
						</ul>
					</div>
					<div className="section-wrapper">
						<div className="profile-info">전화번호</div>
						<InputStyle
							name="phone"
							type="text"
							placeholder="가게 전화번호"
							onChange={productPost}
						/>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">주소</div>
						<InputStyle
							name="address"
							type="text"
							placeholder="가게 주소"
							onChange={productPost}
						/>
					</div>

					<div className="section-wrapper">
						<div className="profile-info">영업 시간</div>
						<div className="timeWrapper">
							<InputStyle
								name="open"
								type="time"
								placeholder="오픈시간"
								onChange={productPost}
							/>
							~
							<InputStyle
								name="close"
								type="time"
								placeholder="마감시간"
								onChange={productPost}
							/>
						</div>
					</div>

					<Button variant="outlined" onClick={post}>
						가게 등록하기
					</Button>
				</div>
			</Wrapper>
		</FormWrapper>
	);
};

export default ProfileAddShop;

const FormWrapper = styled.div`
	position: relative;
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
`;

const EditTitle = styled.div`
	top: 26px;
	left: 37px;
	font-size: 20px;
	width: 350px;
	/* border: 1px solid purple; */
	display: flex;
	align-items: center;
	justify-content: space-between;
	margin-top: 45px;

	p {
		font-size: 20px;
		font-weight: 700;
		color: #646464;
	}
`;

const InputStyle = styled.input`
	outline-style: none;
	border: 1px solid #999;
	border-radius: 4px;
	width: 200px;
	font: inherit;
	padding: 12.5px 14px;
	font-weight: 400;
	font-size: 1rem;
	letter-spacing: 0.00938em;
`;

const Wrapper = styled.div`
	margin: 0;
	padding: 0;
	/* width: 800px; */
	/* min-height: 290px; */
	height: auto;
	/* border: 1px dashed black; */
	margin-top: 10px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	justify-content: space-between;

	.form-wrapper {
		margin-top: 30px;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		min-height: 489px;
		.section-wrapper {
			/* border: 1px dashed green; */
			width: 400px;
			height: 50px;
			justify-content: space-between;
			align-items: center;
			font-size: 18px;
			display: flex;
			flex-direction: row;
			margin-bottom: 16px;
			.timeWrapper {
				width: 220px;
				display: flex;
				justify-content: space-between;
				align-items: center;
			}
		}
		.category {
			height: 120px;
		}
	}

	.input {
		width: 284px;
		height: 47px;
		border-radius: 6px;
	}
	.checkboxDiv {
		width: 284px;
		height: auto;
		padding: 0;
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
		li {
			letter-spacing: -0.009em;
			margin: 0 0.5rem 0.5rem 0;
			vertical-align: top;
			display: inline-block;
		}
	}
`;
