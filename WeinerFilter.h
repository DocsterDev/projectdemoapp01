
/* Read In Desired Data File */ 

	for (k = 0; k < 36; k++) {
		sprintf(fname, "%s%.3d. raw", desiredFilename, k); 
		if ((fpi=fopen(fname, "r") ) == NULL) {
			printf("error reading %s\n", fname);
			exit(1);
		}
		fread(&ptr[k][0][0],2,(58*68), fp1);
	}

	for ( z=0; z<36; z++ ) {
		for ( y=0; y<58; y++ ) {
			for ( x=0; x<68; x++ ) {
				desired[z][y][x] = (double) ptr[z][y][x];
			}
		}
	}
	fclose(fp1);

/* Initialize Wiener Filter Taps to Zero and Set the Converg Coef (mu) */ 

	/* set all tap values = 0 */
	for ( k=0; k<5; k++ ) {
		for ( j=0; j<11; j++ ) {
			for ( i=0; i<11; i++ ) {
				WFilter[k][j][i] = 0;
			}
		}
	}
	NumTaps = 5 * 11 * 11;

	/* calculate total image intensity (power) to set mu */
	TIntensity = 0; 
	for ( k=15; k<23; k++ ) {
		for ( j=24; j<36; j++ ) {
			for ( i=29; i<41; i++ ) {
				IofXYZ = data[k][j][i] * data[k][j][i];
				TIntensity = TIntensity + IofXYZ;
			}
		}
	}
	NumPoints = 8 * 12 * 12;
	mu = (0.50 * NumPoints) / (TIntensity * NumTaps); 

	/* initialize the filtered data points to zero */ 
	for ( k=0; k<36; k++ ) {
		for ( j=0; j<58; j++ ) {
			for ( i=0; i<68; i++ ) {
				filtered[k][j][i] = 0;
			}
		}
	}

	/* Iterate to solve for Filter coefficients */
	for ( q=0; q<48; q++ ) {
		for ( r=1; r<51; r++ ) {
			s = (q * 50) + r;
			printf("Deconvı iteration %d of 2400 in progress.... \n", s); 

			maxValue = 0;
			minValue = 0; 

			for ( k=4; k<32; k++ ) { 
				for ( j=5; j<53; j++ ) { 
					for ( i=5; i<63; i++ ) {

						filtered[k][j][i] = 0; 

						for ( n=0; n<5; n++ ) {
							for ( m=0; m<11; m++ ) {
								for ( l=0; l<11; l++ ) { 
									filtered[k][j][i] = filtered[k][j][i] + (data[k-2+n][j-5+m][i-5+l] * WFilter[n][m][l]);
									maxValue = max(maxValue, filtered[k][j][i]); 
									minValue = min(minValue, filtered[k][j][i]); 
									error = desired[k][j][i] - filtered[k][j][i];
								}
							}
						}

						for ( n=0; n<5; n++ ) {
							for ( m=0; m<11; m++ ) {
								for ( l=0; l<11; l++ ) { 
									WFilter[n][m][l] = WFilter[n][m][l] + (2*mu*error*data[k-2+n][j-5+m][i-5+l]);	
								}
							}
						}
					}
				}
			}
		}
	}

	mu = 0.996 * mu;

	 /* Find Max & Min values of the Filtered Data to Track Convergence */ 
	printf("Iteration %d complete: max=%lf, min=%1f\n", s,maxValue, minValue);

