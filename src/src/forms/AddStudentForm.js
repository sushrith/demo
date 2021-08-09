import React from 'react';
import {Formik} from 'formik';
import {Input,Button,Tag} from 'antd';
import {addNewStudent} from '../client';

const inputButtonMargin={marginBottom:'10px'};
const tagStyle={backgroundColor:'#f50',color:'white',...inputButtonMargin};

const AddStudentForm=(props)=> 

        (
            <Formik
              initialValues={{ email: '', firstName:'',lastName:'',gender:'' }}
              validate={values => {
                const errors = {};
                if(!values.firstName)
                {
                    errors.firstName = 'First Name required';
                }
                if(!values.lastName)
                {
                    errors.lastName = 'Last Name required';
                }
                if (!values.email) {
                  errors.email = 'Email Required';
                } else if (
                  !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.email)
                ) {
                  errors.email = 'Invalid Email address';
                }
                if(!values.gender)
                {
                    errors.gender = 'Gender required';
                }
                else if(!['MALE','male','female','FEMALE'].includes(values.gender))
                {
                    errors.gender = 'Gender must be (MALE,male,female,FEMALE)';
                }
                return errors;
              }}
              onSubmit={(values, { setSubmitting }) => {
                addNewStudent(values).then(()=>{
                    props.onSuccess()
                }).catch(err=>{
                    props.onFailure(err);
                }).finally(()=>{
                    setSubmitting(false);
                })
              }}
            >
              {({
                values,
                errors,
                touched,
                handleChange,
                handleBlur,
                handleSubmit,
                isSubmitting,
                submitForm,
                isValid
                /* and other goodies */
              }) => (
                <form onSubmit={handleSubmit}>
                  <Input
                    style={inputButtonMargin}
                    name="firstName"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.firstName}
                    placeholder="First Name. E.g John"
                  />
                  {errors.firstName && touched.firstName && <Tag style={tagStyle}>{errors.firstName}</Tag> }
                  <Input
                  style={inputButtonMargin}
                    name="lastName"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.lastName}
                    placeholder="Last Name. E.g Dale"
                  />
                  {errors.lastName && touched.lastName && <Tag style={tagStyle}>{errors.lastName}</Tag>}
                  <Input
                  style={inputButtonMargin}
                    name="email"
                    type="email"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.email}
                    placeholder="Email. E.g john@gmail.com"
                  />
                  {errors.email && touched.email && <Tag style={tagStyle}>{errors.email}</Tag>}
                  <Input
                  style={inputButtonMargin}
                    name="gender"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.gender}
                    placeholder="Gender. E.g Male or Female"
                  />
                  {errors.gender && touched.gender && <Tag style={tagStyle}>{errors.gender}</Tag>}
                  
                  <Button onClick={()=>submitForm()} type="submit" disabled={isSubmitting | (touched && !isValid)}>
                    Submit
                  </Button>
                </form>
              )}
            </Formik>
        );
          
export default AddStudentForm;