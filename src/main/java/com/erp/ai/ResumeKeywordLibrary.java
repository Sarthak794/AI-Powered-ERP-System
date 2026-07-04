package com.erp.ai;

import java.util.List;
import java.util.Map;

public class ResumeKeywordLibrary {

    private ResumeKeywordLibrary() {
    }

    public static final List<String> JAVA_SKILLS = List.of(

            "java",
            "core java",
            "java 8",
            "java 11",
            "java 17",
            "java 21",

            "spring",
            "spring boot",
            "spring mvc",
            "spring security",
            "spring data jpa",

            "hibernate",
            "jpa",
            "jdbc",

            "rest",
            "rest api",
            "microservices",

            "maven",
            "gradle",

            "git",
            "github",

            "mysql",
            "postgresql",
            "oracle",
            "mongodb",
            "redis",

            "html",
            "css",
            "bootstrap",
            "javascript",
            "typescript",

            "react",
            "angular",

            "docker",
            "kubernetes",

            "aws",
            "azure",
            "gcp",

            "linux",

            "junit",
            "mockito",

            "postman",

            "eclipse",
            "sts",
            "intellij"
    );

    public static final List<String> SECTION_HEADERS = List.of(

            "summary",
            "objective",

            "education",

            "skills",
            "technical skills",

            "projects",

            "experience",
            "work experience",
            "internship",

            "certifications",

            "achievements",

            "languages",

            "hobbies",

            "contact"
    );
    
    public static final Map<String, List<String>> CATEGORY_KEYWORDS =
    		Map.of(

    		"Backend",
    		List.of(
    		"java",
    		"spring",
    		"spring boot",
    		"spring mvc",
    		"hibernate",
    		"jpa",
    		"jdbc",
    		"rest",
    		"microservices"
    		),

    		"Frontend",
    		List.of(
    		"html",
    		"css",
    		"bootstrap",
    		"javascript",
    		"typescript",
    		"react",
    		"angular",
    		"vue"
    		),

    		"Database",
    		List.of(
    		"mysql",
    		"postgresql",
    		"mongodb",
    		"oracle",
    		"redis"
    		),

    		"Cloud",
    		List.of(
    		"aws",
    		"azure",
    		"gcp"
    		),

    		"DevOps",
    		List.of(
    		"docker",
    		"kubernetes",
    		"jenkins",
    		"github actions",
    		"gitlab ci"
    		),

    		"Testing",
    		List.of(
    		"junit",
    		"mockito",
    		"selenium"
    		),

    		"Tools",
    		List.of(
    		"git",
    		"github",
    		"maven",
    		"gradle",
    		"postman",
    		"intellij",
    		"eclipse",
    		"sts"
    		)

    		);

}	